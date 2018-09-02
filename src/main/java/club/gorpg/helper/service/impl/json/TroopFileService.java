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
public class TroopFileService implements IJsonFileService {
	private static final String FILE_NAME = "data/Troops.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(String fileName, JsonNode tn) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.troop, "敌群");
		int i = 0;
		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {
				fm.add("$[" + i + "].name", new SectionInfo(n.get("name").textValue(), "敌群" + i + "名称"));
				JsonNode pages = n.get("pages");
				if (pages == null || pages.isNull()) {
					continue;
				}
				int j = 0;
				for (JsonNode p : pages) {
					if (p == null || p.isNull()) {
						continue;
					}
					JsonNode list = p.get("list");
					if (list == null || list.isNull()) {
						continue;
					}
					int k = 0;
					for (JsonNode l : list) {
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
								fm.add("$[" + i + "]pages[" + j + "].list[" + k + "].parameters[" + m + "]",
										new SectionInfo(param.textValue(),
												"敌群" + i + "的" + j + "组" + k + "号因素的" + m + "号参数"));
							}
							m++;
						}

						k++;
					}
					j++;
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
