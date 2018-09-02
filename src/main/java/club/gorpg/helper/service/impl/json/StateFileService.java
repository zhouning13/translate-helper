package club.gorpg.helper.service.impl.json;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;

import club.gorpg.helper.model.FileIcon;
import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.FileMeta.SectionInfo;
import club.gorpg.helper.model.GameMeta;
import club.gorpg.helper.model.GameMeta.TreeModel;
import club.gorpg.helper.service.impl.IJsonFileService;

@Service
public class StateFileService implements IJsonFileService {
	private static final String FILE_NAME = "data/States.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(String fileName, JsonNode tn) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.state, "状态");
		int i = 0;
		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {
				fm.add("$[" + i + "].name", new SectionInfo(n.get("name").asText(), "状态" + i + "名称"));
				JsonNode desc = n.get("description");
				if (desc != null && !desc.isNull()) {
					fm.add("$[" + i + "].description", new SectionInfo(desc.asText(), "状态" + i + "描述"));
				}
				fm.add("$[" + i + "].note", new SectionInfo(n.get("note").asText(), "状态" + i + "note"));
				fm.add("$[" + i + "].message1", new SectionInfo(n.get("message1").asText(), "状态" + i + "参数1"));
				fm.add("$[" + i + "].message2", new SectionInfo(n.get("message2").asText(), "状态" + i + "参数2"));
				fm.add("$[" + i + "].message3", new SectionInfo(n.get("message3").asText(), "状态" + i + "参数3"));
				fm.add("$[" + i + "].message4", new SectionInfo(n.get("message4").asText(), "状态" + i + "参数4"));
			}
			i++;
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		gm.getTree().add(new TreeModel(FILE_NAME));
	}
}
