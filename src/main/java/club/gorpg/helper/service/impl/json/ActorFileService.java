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
public class ActorFileService extends AbstractFileService {
	private static final String FILE_NAME = "data/Actors.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(String fileName, JsonNode tn, DocumentContext chinese) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.actor, "角色");

		int i = 0;
		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {
				add(fm, "$[" + i + "].name", "角色" + i + "的名称", n.get("name").textValue(), chinese);
				add(fm, "$[" + i + "].profile", "角色" + i + "的profile", n.get("profile").textValue(), chinese);
				add(fm, "$[" + i + "].nickname", "角色" + i + "的昵称", n.get("nickname").textValue(), chinese);
			}
			i++;
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		gm.getTree().add(new TreeModel(FILE_NAME));
	}

}
