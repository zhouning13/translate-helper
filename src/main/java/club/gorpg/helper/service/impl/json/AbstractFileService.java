package club.gorpg.helper.service.impl.json;

import com.jayway.jsonpath.DocumentContext;

import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.FileMeta.SectionInfo;
import club.gorpg.helper.service.impl.IJsonFileService;

public abstract class AbstractFileService implements IJsonFileService {
	protected void add(FileMeta fm, String path, String name, String foreign, DocumentContext chinese) {
		fm.add(path, new SectionInfo(foreign, chinese.read(path, String.class), name));
	}
}
