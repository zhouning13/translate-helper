package club.gorpg.helper.service.impl.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.DocumentContext;

import club.gorpg.helper.model.FileIcon;
import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.GameMeta;
import club.gorpg.helper.model.GameMeta.TreeModel;

@Service
public class MapInfoFileService extends AbstractFileService {

	private static final String FILE_NAME = "data/MapInfos.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(GameMeta gm, String fileName, JsonNode tn, DocumentContext chinese) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.mapInfo, "地图索引");
		int i = 0;
		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {
				add(fm, "$[" + i + "].name", "地图" + i, n.get("name").textValue(), chinese);
			}
			i++;
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		// 地图信息
		gm.getTree().add(new TreeModel(FILE_NAME));
		//
		TreeModel root = new TreeModel();
		root.setShowName("地图");
		Map<Integer, TreeModel> modelMap = new HashMap<>();
		modelMap.put(0, root);

		for (JsonNode n : tn) {
			if (n != null && !n.isNull()) {
				String mapId = n.get("id").asText();
				String fileName = "data/Map" + StringUtils.leftPad(mapId, 3, "0") + ".json";
				FileMeta fm = gm.getFileMap().get(fileName);
				if (fm != null) {
					int id = n.get("id").asInt();
					int parentId = n.get("parentId").asInt();
					String name = n.get("name").textValue();
					fm.setName(name);
					TreeModel tm = new TreeModel(id, parentId, fileName);
					modelMap.put(id, tm);
				}
			}
		}

		for (TreeModel tm : modelMap.values()) {
			if (tm.getId() == 0) {
				continue;
			}
			TreeModel parent = modelMap.get(tm.getParentId());
			if (parent == null) {
				continue;
			}
			if (parent.getNodes() == null) {
				parent.setNodes(new ArrayList<>());
			}
			parent.getNodes().add(tm);
		}

		gm.getTree().add(root);
	}
}
