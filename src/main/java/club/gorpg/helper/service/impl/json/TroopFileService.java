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
public class TroopFileService extends AbstractFileService {
	private static final String FILE_NAME = "data/Troops.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(GameMeta gm, String fileName, JsonNode tn, DocumentContext chinese) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.troop, "敌群");
		int i = 0;
		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {
				add(fm, "$[" + i + "].name", "敌群" + i + "的名称", n.get("name").textValue(), chinese);
				JsonNode pages = n.get("pages");
				if (pages == null || pages.isNull()) {
					continue;
				}
				int j = -1;
				for (JsonNode p : pages) {
					j++;
					if (p == null || p.isNull()) {
						continue;
					}
					JsonNode list = p.get("list");
					if (list == null || list.isNull()) {
						continue;
					}
					int k = -1;
					for (JsonNode l : list) {
						k++;
						if (l == null || l.isNull()) {
							continue;
						}
						JsonNode params = l.get("parameters");
						if (params == null || params.isNull()) {
							continue;
						}
						int m = 0;
						for (JsonNode param : params) {
							if (param != null && !param.isNull() && param.isTextual()) {
								add(fm, "$[" + i + "]pages[" + j + "].list[" + k + "].parameters[" + m + "]",
										"敌群" + i + "的" + j + "组" + k + "号因素的" + m + "号参数", param.textValue(), chinese);
							}
							m++;
						}
					}

				}
			}
			i++;
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		gm.getTree().add(new TreeModel(FILE_NAME));
	}
}
