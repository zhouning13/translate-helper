package club.gorpg.helper.service.impl.json;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;

import club.gorpg.helper.model.FileIcon;
import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.FileMeta.SectionInfo;
import club.gorpg.helper.model.GameMeta;
import club.gorpg.helper.model.GameMeta.TreeModel;
import club.gorpg.helper.service.impl.IJsonFileService;

@Service
public class CommonEventFileService implements IJsonFileService {
	private static final String FILE_NAME = "data/CommonEvents.json";
	private static final int BLOCK_SIZE = 20;

	public boolean accept(String fileName) {
		if (fileName.equals(FILE_NAME))
			return true;
		if (!fileName.startsWith("data/CommonEvents")) {
			return false;
		}
		String eventNumber = StringUtils.substringBetween(fileName, "data/CommonEvents", ".json");

		return NumberUtils.isDigits(eventNumber);

	}

	public List<FileMeta> getFileMeta(String fileName, JsonNode tn) {
		List<FileMeta> fms = new ArrayList<>();
		FileMeta fm = null;
		int i = 0;
		for (JsonNode n : tn) {
			if (i % BLOCK_SIZE == 0) {
				int index = i / BLOCK_SIZE;
				fm = new FileMeta(FILE_NAME, "data/CommonEvents" + index + ".json", FileIcon.event, "公共事件" + index);
				fms.add(fm);
			}

			if (n != null && !n.isNull()) {
				// fm.add("$[" + i + "].name", new SectionInfo(n.get("name").textValue(), "公共事件"
				// + i));
				String eventName = n.get("name").textValue();// 事件名称

				JsonNode list = n.get("list");
				if (list == null || list.isNull()) {
					continue;
				}
				int j = 0;
				for (JsonNode node : list) {

					int code = node.get("code").asInt();

					if (code == 401) {
						// 401 文本类
						JsonNode text = node.get("parameters").get(0);
						fm.add("$.events[" + i + "].list[" + j + "].parameters[0]",
								new SectionInfo(text.asText(), "公共事件" + i + "“" + eventName + "”的" + j + "行的文本"));
					}

					if (code == 102) {
						// 102 选项
						JsonNode params = node.get("parameters").get(0);
						if (params != null && !params.isNull()) {
							int m = 0;
							for (JsonNode param : params) {
								if (param != null && !param.isNull()) {
									fm.add("$.events[" + i + "].list[" + j + "].parameters[0][" + m + "]",
											new SectionInfo(param.textValue(),
													"公共事件" + i + "“" + eventName + "”的" + j + "行选择的第" + m + "个选项"));
								}
								m++;
							}
						}

					}

					if (code == 402) {
						// 402 选项被选择
						JsonNode params = node.get("parameters");
						JsonNode index = params.get(0);
						JsonNode text = params.get(1);
						fm.add("$.events[" + i + "].list[" + j + "].parameters[1]", new SectionInfo(text.textValue(),
								"事件" + i + "“" + eventName + "”的" + j + "行选择的第" + index + "个选项，需与前面的选项翻译一致！"));
					}
					j++;
				}

			}
			i++;
		}
		return fms;
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		TreeModel root = new TreeModel();
		root.setShowName("公共事件");
		List<TreeModel> tms = new ArrayList<>();
		int size = tn.size() / BLOCK_SIZE;
		for (int i = 0; i < size; i++) {
			tms.add(new TreeModel("data/CommonEvents" + i + ".json"));
		}
		root.setNodes(tms);
		gm.getTree().add(root);
	}
}
