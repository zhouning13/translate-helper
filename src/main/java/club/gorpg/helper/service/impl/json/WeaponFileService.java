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
public class WeaponFileService implements IJsonFileService {
	private static final String FILE_NAME = "data/Weapons.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(String fileName, JsonNode tn) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.weapon, "武器");
		int i = 0;
		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {
				fm.add("$[" + i + "].name", new SectionInfo(n.get("name").textValue(), "武器" + i + "名称"));
				fm.add("$[" + i + "].note", new SectionInfo(n.get("note").textValue(), "武器" + i + "的note"));
				fm.add("$[" + i + "].description", new SectionInfo(n.get("description").textValue(), "武器" + i + "描述"));
			}
			i++;
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		gm.getTree().add(new TreeModel(FILE_NAME));
	}
}