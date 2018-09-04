package club.gorpg.helper.service.impl.json;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.DocumentContext;

import club.gorpg.helper.model.FileIcon;
import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.GameMeta;
import club.gorpg.helper.model.GameMeta.TreeModel;

@Service
public class SystemFileService extends AbstractFileService {
	private static final String FILE_NAME = "data/System.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(String fileName, JsonNode root, DocumentContext chinese) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.system, "系统");

		JsonNode terms = root.get("terms");
		if (terms != null && !terms.isNull()) {
			JsonNode messages = terms.get("messages");
			if (messages != null && !messages.isNull()) {
				for (Iterator<Map.Entry<String, JsonNode>> fields = messages.fields(); fields.hasNext();) {
					Entry<String, JsonNode> e = fields.next();
					JsonNode n = e.getValue();
					if (n != null && !n.isNull()) {
						add(fm, "$.terms.messages." + e.getKey(), "系统消息" + e.getKey(), n.textValue(), chinese);
					}
				}
			}

			JsonNode params = terms.get("params");
			if (params != null && !params.isNull()) {
				int i = 0;
				for (JsonNode st : params) {
					if (st != null && !st.isNull()) {
						add(fm, "$.terms.params[" + i + "]", "参数" + i, st.textValue(), chinese);
					}
					i++;
				}
			}

			JsonNode commands = terms.get("commands");
			if (commands != null && !commands.isNull()) {
				int i = 0;
				for (JsonNode st : commands) {
					if (st != null && !st.isNull()) {
						add(fm, "$.terms.commands[" + i + "]", "操作" + i, st.textValue(), chinese);
					}
					i++;
				}
			}

			JsonNode basic = terms.get("basic");
			if (basic != null && !basic.isNull()) {
				int i = 0;
				for (JsonNode st : basic) {
					if (st != null && !st.isNull()) {
						add(fm, "$.terms.basic[" + i + "]", "基础属性" + i, st.textValue(), chinese);
					}
					i++;
				}
			}
		}

		JsonNode weaponTypes = root.get("weaponTypes");
		if (weaponTypes != null && !weaponTypes.isNull()) {
			int i = 0;
			for (JsonNode st : weaponTypes) {
				if (st != null && !st.isNull()) {
					add(fm, "$.weaponTypes[" + i + "]", "武器类型" + i, st.textValue(), chinese);
				}
				i++;
			}
		}

		JsonNode variables = root.get("variables");
		if (variables != null && !variables.isNull()) {
			int i = 0;
			for (JsonNode st : variables) {
				if (st != null && !st.isNull()) {
					add(fm, "$.variables[" + i + "]", "变量" + i, st.textValue(), chinese);
				}
				i++;
			}
		}

		JsonNode switches = root.get("switches");
		if (switches != null && !switches.isNull()) {
			int i = 0;
			for (JsonNode st : switches) {
				if (st != null && !st.isNull()) {
					add(fm, "$.switches[" + i + "]", "开关" + i, st.textValue(), chinese);
				}
				i++;
			}
		}

		JsonNode skillTypes = root.get("skillTypes");
		if (skillTypes != null && !skillTypes.isNull()) {
			int i = 0;
			for (JsonNode st : skillTypes) {
				if (st != null && !st.isNull()) {
					add(fm, "$.skillTypes[" + i + "]", "技能类型" + i, st.textValue(), chinese);
				}
				i++;
			}
		}

		JsonNode equipTypes = root.get("equipTypes");
		if (equipTypes != null && !equipTypes.isNull()) {
			int i = 0;
			for (JsonNode et : equipTypes) {
				if (et != null && !et.isNull()) {
					add(fm, "$.equipTypes[" + i + "]", "装备类型" + i, et.textValue(), chinese);
				}
				i++;
			}
		}

		JsonNode elements = root.get("elements");
		if (elements != null && !elements.isNull()) {
			int i = 0;
			for (JsonNode e : elements) {
				if (e != null && !e.isNull()) {
					add(fm, "$.elements[" + i + "]", "元素属性" + i, e.textValue(), chinese);
				}
				i++;
			}
		}

		JsonNode armorTypes = root.get("armorTypes");
		if (armorTypes != null && !armorTypes.isNull()) {
			int i = 0;
			for (JsonNode at : armorTypes) {
				if (at != null && !at.isNull()) {
					add(fm, "$.armorTypes[" + i + "]", "装甲类型" + i, at.textValue(), chinese);
				}
				i++;
			}
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		gm.getTree().add(new TreeModel(FILE_NAME));
	}
}
