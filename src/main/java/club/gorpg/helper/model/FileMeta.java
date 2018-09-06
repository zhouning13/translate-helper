package club.gorpg.helper.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class FileMeta implements Serializable {
	private static final long serialVersionUID = -8705399616754838481L;
	@JsonProperty("sn")
	private String sourceName;
	@JsonProperty("f")
	private String fileName;
	@JsonProperty("tp")
	private FileIcon template;
	@JsonProperty("t")
	private String tempIcon;
	@JsonProperty("n")
	private String name;
	@JsonProperty("to")
	private int total;
	@JsonProperty("tr")
	private int trans;
	@JsonProperty("fw")
	private int foreignWords;
	@JsonProperty("tw")
	private int transWords;
	@JsonProperty("p")
	private double percent;
	@JsonProperty("m")
	private Map<String, SectionInfo> sectionMap;
	@JsonProperty("s")
	private List<String> sections;

	public FileMeta() {
		super();
	}

	public FileMeta(String fileName, FileIcon template, String name) {
		super();
		this.sourceName = fileName;
		this.fileName = fileName;
		this.template = template;
		this.name = name;
	}

	public FileMeta(String sourceName, String fileName, FileIcon template, String name) {
		super();
		this.sourceName = sourceName;
		this.fileName = fileName;
		this.template = template;
		this.name = name;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public FileIcon getTemplate() {
		return template;
	}

	public void setTemplate(FileIcon template) {
		this.template = template;
	}

	public String getTempIcon() {
		return template == null ? null : template.getIcon();
	}

	public void setTempIcon(String tempIcon) {
		// DO NOTHING
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getForeignWords() {
		return foreignWords;
	}

	public void setForeignWords(int foreignWords) {
		this.foreignWords = foreignWords;
	}

	public int getTransWords() {
		return transWords;
	}

	public void setTransWords(int transWords) {
		this.transWords = transWords;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getTrans() {
		return trans;
	}

	public void setTrans(int trans) {
		this.trans = trans;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public Map<String, SectionInfo> getSectionMap() {
		return sectionMap;
	}

	public void setSectionMap(Map<String, SectionInfo> sectionMap) {
		this.sectionMap = sectionMap;
	}

	public List<String> getSections() {
		return sections;
	}

	public void setSections(List<String> sections) {
		this.sections = sections;
	}

	@JsonIgnore
	public void add(String path, SectionInfo section) {
		if (sectionMap == null) {
			sectionMap = new HashMap<>();
			sections = new ArrayList<>();
		}

		if (sections == null) {
			sections = new ArrayList<>();
		}
		sectionMap.put(path, section);
		sections.add(path);
	}

	@JsonIgnore
	public void recount() {
		if (sectionMap != null && !sectionMap.isEmpty()) {
			int trans = 0;
			int transWords = 0;
			int foreignWords = 0;
			for (SectionInfo e : this.sectionMap.values()) {
				foreignWords += e.getForeignWords();
				if (e.getTrans()) {
					trans++;
					transWords += e.getTransWords();
				}
			}
			setTrans(trans);
			setTransWords(transWords);
			setForeignWords(foreignWords);
			int total = this.sectionMap.size();
			setTotal(total);
			if (total != 0) {
				setPercent(new BigDecimal(trans * 100).divide(new BigDecimal(total), 2, BigDecimal.ROUND_HALF_EVEN)
						.doubleValue());
			}
		} else {
			setTrans(0);
			setTotal(0);
			setPercent(0);
		}
	}

	@JsonInclude(value = Include.NON_NULL)
	public static class SectionInfo implements Serializable {
		private static final long serialVersionUID = -1299518836373322188L;
		@JsonProperty("p")
		private String position;
		@JsonProperty("fc")
		private String foreignContent;
		@JsonProperty(value = "tc", required = false)
		private String transContent;
		@JsonProperty("fw")
		private int foreignWords;
		@JsonProperty("tw")
		private int transWords;
		@JsonProperty("t")
		private boolean trans;

		public SectionInfo() {
			super();
		}

		public SectionInfo(int foreignWords, int transWords, boolean trans) {
			super();
			this.foreignWords = foreignWords;
			this.transWords = transWords;
			this.trans = trans;
		}

		public SectionInfo(String foreignContent, String transContent, String position) {
			super();
			this.position = position;
			this.foreignContent = foreignContent;
			this.foreignWords = StringUtils.length(foreignContent);
			if (foreignWords == 0) {
				// 日文长度为0，标记已翻译
				this.transWords = 0;
				this.trans = true;
				return;
			}

			if (Objects.equals(foreignContent, transContent)) {
				// 中文和日文一致，则表示没有翻译
				this.trans = false;
				this.transWords = 0;
			} else {
				// 中日文不一致，标记已翻译
				this.trans = true;
				this.transWords = StringUtils.length(transContent);
				this.transContent = transContent;
			}
		}

		public String getPosition() {
			return position;
		}

		public void setPosition(String position) {
			this.position = position;
		}

		public String getForeignContent() {
			return foreignContent;
		}

		public void setForeignContent(String foreignContent) {
			this.foreignContent = foreignContent;
		}

		public String getTransContent() {
			return transContent;
		}

		public void setTransContent(String transContent) {
			this.transContent = transContent;
		}

		public int getForeignWords() {
			return foreignWords;
		}

		public void setForeignWords(int foreignWords) {
			this.foreignWords = foreignWords;
		}

		public int getTransWords() {
			return transWords;
		}

		public void setTransWords(int transWords) {
			this.transWords = transWords;
		}

		public boolean getTrans() {
			return trans;
		}

		public void setTrans(boolean trans) {
			this.trans = trans;
		}

	}
}
