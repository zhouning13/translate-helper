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
public class StateFileService extends AbstractFileService {
	private static final String FILE_NAME = "data/States.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(String fileName, JsonNode tn, DocumentContext chinese) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.state, "状态");
		int i = 0;
		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {

				add(fm, "$[" + i + "].name", "状态" + i + "名称", n.get("name").textValue(), chinese);

				JsonNode desc = n.get("description");
				if (desc != null && !desc.isNull()) {
					add(fm, "$[" + i + "].description", "状态" + i + "描述", desc.textValue(), chinese);
				}

				add(fm, "$[" + i + "].note", "状态" + i + "note", n.get("note").textValue(), chinese);
				add(fm, "$[" + i + "].message1", "状态" + i + "参数1", n.get("message1").textValue(), chinese);
				add(fm, "$[" + i + "].message2", "状态" + i + "参数2", n.get("message2").textValue(), chinese);
				add(fm, "$[" + i + "].message3", "状态" + i + "参数3", n.get("message3").textValue(), chinese);
				add(fm, "$[" + i + "].message4", "状态" + i + "参数4", n.get("message4").textValue(), chinese);
			}
			i++;
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		gm.getTree().add(new TreeModel(FILE_NAME));
	}
}
