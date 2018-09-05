package club.gorpg.helper.service.impl.json;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.DocumentContext;

import club.gorpg.helper.model.FileIcon;
import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.GameMeta;
import club.gorpg.helper.model.GameMeta.TreeModel;

@Service
public class SkillFileService extends AbstractFileService {
	private static final String FILE_NAME = "data/Skills.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(GameMeta gm, String fileName, JsonNode tn, DocumentContext chinese) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.skill, "技能");
		int i = 0;
		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {
				add(fm, "$[" + i + "].name", "技能" + i + "的名称", n.get("name").textValue(), chinese);
				add(fm, "$[" + i + "].description", "技能" + i + "的描述", n.get("description").textValue(), chinese);
				add(fm, "$[" + i + "].note", "技能" + i + "的note", n.get("note").textValue(), chinese);
				add(fm, "$[" + i + "].message1", "技能" + i + "的消息1", n.get("message1").textValue(), chinese);
				add(fm, "$[" + i + "].message2", "技能" + i + "的消息2", n.get("message2").textValue(), chinese);
			}
			i++;
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		gm.getTree().add(new TreeModel(FILE_NAME));
	}
}
