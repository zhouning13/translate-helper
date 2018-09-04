package club.gorpg.helper.service.impl.json;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.DocumentContext;

import club.gorpg.helper.model.FileIcon;
import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.GameMeta;

@Service
public class MapFileService extends AbstractFileService {

	public boolean accept(String fileName) {
		if (!fileName.startsWith("data/Map"))
			return false;
		String mapNumber = StringUtils.substringBetween(fileName, "data/Map", ".json");
		return NumberUtils.isDigits(mapNumber);
	}

	public List<FileMeta> getFileMeta(String fileName, JsonNode tn, DocumentContext chinese) {
		String mapNumber = StringUtils.substringBetween(fileName, "data/Map", ".json");
		FileMeta fm = new FileMeta(fileName, FileIcon.map, "地图" + mapNumber);

		JsonNode events = tn.get("events");
		if (events != null && !events.isNull()) {
			int i = 0;
			for (JsonNode event : events) {
				if (event != null && !event.isNull()) {
					// fm.add("$.events[" + i + "].name", new SectionInfo(, "事件" + i));
					String eventName = event.get("name").textValue();// 事件名称

					JsonNode pages = event.get("pages");
					if (pages != null && !pages.isNull()) {
						int j = 0;
						for (JsonNode p : pages) {
							if (p != null && !p.isNull()) {

								JsonNode list = p.get("list");
								if (list != null && !list.isNull()) {
									int k = 0;
									for (JsonNode l : list) {
										if (l != null && !l.isNull()) {
											int code = l.get("code").asInt();

											if (code == 401) {
												// 401 文本类
												JsonNode text = l.get("parameters").get(0);
												String path = "$.events[" + i + "].pages[" + j + "].list[" + k
														+ "].parameters[0]";
												String name = "事件" + i + "“" + eventName + "”的" + j + "页" + k + "行的文本";
												add(fm, path, name, text.textValue(), chinese);
											}

											if (code == 102) {
												// 102 选项
												JsonNode params = l.get("parameters").get(0);
												if (params != null && !params.isNull()) {
													int m = 0;
													for (JsonNode param : params) {
														if (param != null && !param.isNull()) {
															String path = "$.events[" + i + "].pages[" + j + "].list["
																	+ k + "].parameters[0][" + m + "]";
															String name = "事件" + i + "“" + eventName + "”的" + j + "页"
																	+ k + "行选择的第" + m + "个选项";
															add(fm, path, name, param.textValue(), chinese);
														}
														m++;
													}
												}

											}

											if (code == 402) {
												// 402 选项被选择
												JsonNode params = l.get("parameters");
												JsonNode index = params.get(0);
												JsonNode text = params.get(1);
												String path = "$.events[" + i + "].pages[" + j + "].list[" + k
														+ "].parameters[1]";
												String name = "事件" + i + "“" + eventName + "”的" + j + "页" + k + "行选择的第"
														+ index + "个选项，需与前面的选项翻译一致！";
												add(fm, path, name, text.textValue(), chinese);
											}

										}
										k++;
									}
								}
							}
							j++;
						}
					}
				}
				i++;
			}
		}
		return Lists.newArrayList(fm);
	}

	public void afterHandle(GameMeta gm, JsonNode tn) {
		// DO NOTHING
	}
}
