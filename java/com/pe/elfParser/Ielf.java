package com.pe.elfParser;

import org.python.core.PyString;

public interface Ielf {
	 public boolean startParse();
	 public PyString getEI_NIDENT();
	 public PyString getHeader();
	 public PyString getSections();
	 public PyString getPrograms();
	 public PyString getCreateTime();
	 public PyString getFileName();
	 public PyString getModifyTime();
	 public PyString getFileSize();
	 public void createarff();
}
