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
public class ItemFileService extends AbstractFileService {
	private static final String FILE_NAME = "data/Items.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(GameMeta gm, String fileName, JsonNode tn, DocumentContext chinese) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.item, "道具");
		int i = 0;
		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {
				add(fm, "$[" + i + "].name", "道具" + i + "名称", n.get("name").textValue(), chinese);
				add(fm, "$[" + i + "].description", "道具" + i + "描述", n.get("description").textValue(), chinese);
			}
			i++;
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		gm.getTree().add(new TreeModel(FILE_NAME));
	}
}
