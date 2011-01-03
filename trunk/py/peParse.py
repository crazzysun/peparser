'''
Created on 2010-11-8

@author: Administrator
'''
from com.pe.virus import Ipe
import os
import pefile
import os.path

class peParse(Ipe):
    '''
    classdocs
    '''
    def __init__(self,filename):
        '''
        Constructor
        '''
        self.filename = filename
        self.bin = None
    
    def getFileName(self):
        return self.filename

    def getFileSize(self):
        return str(os.path.getsize(self.filename)/1024)

    def createarff(self):
        outfile = file(self.filename + ".arff", 'w+b')
        outfile.write('%s\r\n' % "@relation 'virus'")
        outfile.write('%s\r\n' % "@attribute SECUR32.DLL {0,1}")
        outfile.write('%s\r\n' % "@attribute SHLWAPI.DLL {0,1}")
        outfile.write('%s\r\n' % "@attribute ImageBase numeric")
        outfile.write('%s\r\n' % "@attribute CheckSum numeric")
        outfile.write('%s\r\n' % "@attribute SizeOfStackReserve numeric")
        outfile.write('%s\r\n' % "@attribute IMAGE_DIRECTORY_ENTRY_SECURITY.Size numeric")
        outfile.write('%s\r\n' % "@attribute IMAGE_DIRECTORY_ENTRY_DEBUG.Size numeric")
        outfile.write('%s\r\n' % "@attribute text.PointerToRelocations numeric")
        outfile.write('%s\r\n' % "@attribute rsrc.Characteristics numeric")
        outfile.write('%s\r\n' % "@attribute RT_MESSAGETABLE numeric")
        outfile.write('%s\r\n' % "@attribute RT_GROUP_ICON numeric")
        outfile.write('%s\r\n' % "@attribute RT_VERSION numeric")
        outfile.write('%s\r\n' % "@attribute result {virus,begnign}")
        outfile.write('%s\r\n' % "@data")

        dlls = []
        testdlls = ['SECUR32.DLL','SHLWAPI.DLL']
        try:
            for f in pe.DIRECTORY_ENTRY_IMPORT:
                dlls.append(f.dll.upper())
        except Exception:
            hi = 0
        for f in testdlls:
            if f in dlls:
                outfile.write('%s,' % 1)
            else:
                outfile.write('%s,' % 0)
                
        outfile.write('%s,' % self.bin.OPTIONAL_HEADER.ImageBase)
        outfile.write('%s,' % self.bin.OPTIONAL_HEADER.CheckSum)
        outfile.write('%s,' % self.bin.OPTIONAL_HEADER.SizeOfStackReserve)

        count = 0
        for f in self.bin.OPTIONAL_HEADER.DATA_DIRECTORY:
            if count == 4:
                outfile.write('%s,' % f.VirtualAddress)
            elif count == 6:
                outfile.write('%s,' % f.Size)
            count += 1
        if count < 4:
            outfile.write('%s,' % 0)
            outfile.write('%s,' % 0)
        elif count < 6:
            outfile.write('%s,' % 0)

        have=0
        for f in self.bin.sections:
            if f.Name.startswith(".text"):
                outfile.write('%s,' % f.PointerToRelocations)
                have = 1
        if have == 0:
            outfile.write('%s,' % 0)
            
        have = 0
        for f in self.bin.sections:
            if f.Name.startswith(".rsrc"):
                outfile.write('%s,' % f.Characteristics)
                have = 1
        if have == 0:
            outfile.write('%s,' % 0)

        counts = []
        try:
            for f in self.bin.DIRECTORY_ENTRY_RESOURCE.entries:
                if f.id == 11:
                    counts.append(11)
                    outfile.write('%s,' % (f.directory.struct.NumberOfNamedEntries+f.directory.struct.NumberOfIdEntries))
                elif f.id == 14:
                    counts.append(14)
                    outfile.write('%s,' % (f.directory.struct.NumberOfNamedEntries+f.directory.struct.NumberOfIdEntries))
                elif f.id == 24:
                    counts.append(24)
                    outfile.write('%s,' % (f.directory.struct.NumberOfNamedEntries+f.directory.struct.NumberOfIdEntries))
            if 11 not in counts:
                outfile.write('%s,' % 0)
            if 14 not in counts:
                outfile.write('%s,' % 0)
            if 24 not in counts:
                outfile.write('%s,' % 0)
        except Exception :
            outfile.write('%s,' % 0)
            outfile.write('%s,' % 0)
            outfile.write('%s,' % 0)
        outfile.write('%s' % "begnign")
        outfile.close()
        
    def startParse(self):
        try:
            self.bin = pefile.PE(self.filename)
            return True
        except Exception:
            return False
