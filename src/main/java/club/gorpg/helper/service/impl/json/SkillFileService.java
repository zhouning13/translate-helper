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
public class SkillFileService implements IJsonFileService {
	private static final String FILE_NAME = "data/Skills.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(String fileName, JsonNode tn) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.skill, "技能");
		int i = 0;
		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {
				fm.add("$[" + i + "].name", new SectionInfo(n.get("name").textValue(), "技能" + i + "名称"));
				fm.add("$[" + i + "].description", new SectionInfo(n.get("description").textValue(), "技能" + i + "描述"));
				fm.add("$[" + i + "].note", new SectionInfo(n.get("note").textValue(), "技能" + i + "note"));
				fm.add("$[" + i + "].message1", new SectionInfo(n.get("message1").textValue(), "技能" + i + "消息1"));
				fm.add("$[" + i + "].message2", new SectionInfo(n.get("message2").textValue(), "技能" + i + "消息2"));
			}
			i++;
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		gm.getTree().add(new TreeModel(FILE_NAME));
	}
}
