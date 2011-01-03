'''
Created on 2010-11-8

@author: Administrator
'''
from com.pe.elfParser import Ielf
from elf.elf_binary import Elf
from elf.printers import printHeader,createHtmlHeader
from elf.section import shdr_type
from elf.elf_header import ehdr_machine
from elf.program import phdr_flags, phdr_type
from optparse import OptionParser, make_option
import os.path
import time
import sys
class elfParse(Ielf):
    '''
    classdocs
    '''


    def __init__(self,filename):
        '''
        Constructor
        '''
        self.filename = filename
        self.bin = None
    
    def getEI_NIDENT(self):
        return createHtmlHeader(self.bin.header.e_ident)

    def getHeader(self):
        return createHtmlHeader(self.bin.header)
    
    def getSections(self):
        result = ""
        ndx = 0
        for sec in self.bin.sections:
            result = result +"<tr bgcolor='white'><td colspan='2'>" + 'Section Header #%d - %s' % (ndx, sec.name) + "</td></tr>"
            ndx += 1
            result = result + createHtmlHeader(sec.header)
            htype = sec.header.sh_type
            if htype == shdr_type['SHT_SYMTAB'] or htype == shdr_type['SHT_DYNSYM']:
                result = result +"<tr><td><td><table><tr><td colspan='2'>" +  'Symbol tables entries:</td></tr>'
                sym_ndx = 0
                for entry in sec.symtab:
                    result = result +"<tr  bgcolor='white'><td colspan='2'>" + '%d :  %s' % (sym_ndx, entry.name) + "</td></tr>"
                    result = result + '<tr class="head"><td width="20%">Description</td><td  width="80%" >Value</td></tr>'
                    result = result + createHtmlHeader(entry)
                    sym_ndx += 1
                result = result +"</table></td></tr>"
            elif htype == shdr_type['SHT_DYNAMIC']:
                result = result +"<tr><td><td><table><tr><td colspan='2'>" +  'Dynamic tables entries:</td></tr>'
                dyn_ndx = 0
                for entry in sec.dynamic:
                    result = result +"<tr bgcolor='white'><td colspan='2'>" +  '%d' % (dyn_ndx) + "</td></tr>"
                    result = result + '<tr class="head"><td width="20%">Description</td><td  width="80%" >Value</td></tr>'
                    result = result + createHtmlHeader(entry)
                    dyn_ndx += 1
                result = result +"</table></td></tr>"
            elif htype == shdr_type['SHT_NOTE']:
                result = result +"<tr><td><td><table><tr><td colspan='2'>" +  'Note entrie(s):</td></tr>'
                note_ndx = 0
                for entry in sec.note:
                    result = result +"<tr  bgcolor='white'><td colspan='2'>" +  '%d' % (note_ndx) + "</td></tr>"
                    result = result + '<tr class="head"><td width="20%">Description</td><td  width="80%" >Value</td></tr>'
                    result = result + createHtmlHeader(entry.header)
                    result = result +"<tr><td>" + ' name: %s' % (entry.name) + "</td>"
                    result = result +"<td>" + ' desc: %s' % (entry.desc) + "</td></tr>"
                    note_ndx += 1
                result = result +"</table></td></tr>"
        return result
    
    def getPrograms(self):
        result = ""
        ndx = 0
        for prg in self.bin.programs:
            result = result +"<tr bgcolor='white'><td colspan='2'>" +  '(Program Header) #%d' % (ndx) + "</td></tr>"
            result = result + createHtmlHeader(prg.header)
            ndx += 1
        return result
    
    def getFileName(self):
        return self.filename

    def getFileSize(self):
        return str(os.path.getsize(self.filename)/1024)

    def getCreateTime(self):
        tmp = time.localtime(os.path.getctime(self.filename))
        return " %s-%s-%s %s:%s:%s " % (tmp.tm_year,tmp.tm_mon,tmp.tm_mday,tmp.tm_hour,tmp.tm_min,tmp.tm_sec)

    def getModifyTime(self):
        tmp = time.localtime(os.path.getmtime(self.filename))
        return " %s-%s-%s %s:%s:%s " % (tmp.tm_year,tmp.tm_mon,tmp.tm_mday,tmp.tm_hour,tmp.tm_min,tmp.tm_sec)
        
    def createarff(self):
        outfile = file(self.filename + ".arff", 'w+b')
        outfile.write('%s\r\n' % "@relation 'virus'")
        outfile.write('%s\r\n' % "@attribute header.e_phnum numeric")
        outfile.write('%s\r\n' % "@attribute header.e_shnum numeric")
        outfile.write('%s\r\n' % "@attribute SHT_DYNSYM.header.sh_link numeric")
        outfile.write('%s\r\n' % "@attribute PT_LOAD1.header.p_vaddr numeric")
        outfile.write('%s\r\n' % "@attribute PT_LOAD2.header.p_offset numeric")
        outfile.write('%s\r\n' % "@attribute result {virus,begnign}")
        outfile.write('%s\r\n' % "@data")
        outfile.write('%s,' % self.bin.header.e_phnum)
        outfile.write('%s,' % self.bin.header.e_shnum)
        have = 0
        for sec in self.bin.sections:
            htype = sec.header.sh_type
            if htype == shdr_type['SHT_DYNSYM']:
                outfile.write('%s,' % sec.header.sh_link)
                have = 1
                break
        if have == 0 :
            outfile.write('%s,' % 0);
        have=0
        for prg in self.bin.programs:
            if prg.header.p_type == phdr_type['PT_LOAD']:
                if have == 0:
                    outfile.write('%s,' % prg.header.p_vaddr)
                    have = have + 1
                elif have == 1:
                    outfile.write('%s,' % prg.header.p_offset)
                    have = have + 1
                    break
        for k in range(have,2):
            outfile.write('%s,' % 0)
        outfile.write('%s' % "begnign")
        outfile.close()
        
    def startParse(self):
        try:
            self.bin = Elf(self.filename)
            return True
        except Exception:
            return False
