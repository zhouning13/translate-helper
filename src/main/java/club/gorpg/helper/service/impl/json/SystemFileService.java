package club.gorpg.helper.service.impl.json;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
public class SystemFileService implements IJsonFileService {
	private static final String FILE_NAME = "data/System.json";

	public boolean accept(String fileName) {
		return fileName.equals(FILE_NAME);
	}

	public List<FileMeta> getFileMeta(String fileName, JsonNode root) {
		FileMeta fm = new FileMeta(FILE_NAME, FileIcon.system, "系统");

		JsonNode terms = root.get("terms");
		if (terms != null && !terms.isNull()) {
			JsonNode messages = terms.get("messages");
			if (messages != null && !messages.isNull()) {
				for (Iterator<Map.Entry<String, JsonNode>> fields = messages.fields(); fields.hasNext();) {
					Entry<String, JsonNode> e = fields.next();
					JsonNode n = e.getValue();
					if (n != null && !n.isNull()) {
						fm.add("$.terms.messages." + e.getKey(), new SectionInfo(n.textValue(), "系统消息" + e.getKey()));
					}
				}
			}

			JsonNode params = terms.get("params");
			if (params != null && !params.isNull()) {
				int i = 0;
				for (JsonNode st : params) {
					if (st != null && !st.isNull()) {
						fm.add("$.terms.params[" + i + "]", new SectionInfo(st.textValue(), "参数" + i));
					}
					i++;
				}
			}

			JsonNode commands = terms.get("commands");
			if (commands != null && !commands.isNull()) {
				int i = 0;
				for (JsonNode st : commands) {
					if (st != null && !st.isNull()) {
						fm.add("$.terms.commands[" + i + "]", new SectionInfo(st.textValue(), "操作" + i));
					}
					i++;
				}
			}

			JsonNode basic = terms.get("basic");
			if (basic != null && !basic.isNull()) {
				int i = 0;
				for (JsonNode st : basic) {
					if (st != null && !st.isNull()) {
						fm.add("$.terms.basic[" + i + "]", new SectionInfo(st.textValue(), "基础属性" + i));
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
					fm.add("$.weaponTypes[" + i + "]", new SectionInfo(st.textValue(), "武器类型" + i));
				}
				i++;
			}
		}

		JsonNode variables = root.get("variables");
		if (variables != null && !variables.isNull()) {
			int i = 0;
			for (JsonNode st : variables) {
				if (st != null && !st.isNull()) {
					fm.add("$.variables[" + i + "]", new SectionInfo(st.textValue(), ""));
				}
				i++;
			}
		}

		JsonNode switches = root.get("switches");
		if (switches != null && !switches.isNull()) {
			int i = 0;
			for (JsonNode st : switches) {
				if (st != null && !st.isNull()) {
					fm.add("$.switches[" + i + "]", new SectionInfo(st.textValue(), ""));
				}
				i++;
			}
		}

		JsonNode skillTypes = root.get("skillTypes");
		if (skillTypes != null && !skillTypes.isNull()) {
			int i = 0;
			for (JsonNode st : skillTypes) {
				if (st != null && !st.isNull()) {
					fm.add("$.skillTypes[" + i + "]", new SectionInfo(st.textValue(), "技能类型" + i));
				}
				i++;
			}
		}

		JsonNode equipTypes = root.get("equipTypes");
		if (equipTypes != null && !equipTypes.isNull()) {
			int i = 0;
			for (JsonNode et : equipTypes) {
				if (et != null && !et.isNull()) {
					fm.add("$.equipTypes[" + i + "]", new SectionInfo(et.textValue(), "装备类型" + i));
				}
				i++;
			}
		}

		JsonNode elements = root.get("elements");
		if (elements != null && !elements.isNull()) {
			int i = 0;
			for (JsonNode e : elements) {
				if (e != null && !e.isNull()) {
					fm.add("$.elements[" + i + "]", new SectionInfo(e.textValue(), "元素属性" + i));
				}
				i++;
			}
		}

		JsonNode armorTypes = root.get("armorTypes");
		if (armorTypes != null && !armorTypes.isNull()) {
			int i = 0;
			for (JsonNode at : armorTypes) {
				if (at != null && !at.isNull()) {
					fm.add("$.armorTypes[" + i + "]", new SectionInfo(at.textValue(), "装甲类型" + i));
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
