package club.gorpg.helper.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(value = Include.NON_NULL)
public class GameMeta implements Serializable {
	private static final long serialVersionUID = 3693389871749990796L;
	private String cname;
	private String fname;
	private String id;

	private Map<String, FileMeta> fileMap;
	private List<TreeModel> tree;
	private int allTotal;
	private int allForeignWords;
	private int allTransWords;
	private int allTrans;
	private double percent;
	private int[] encryptionKey;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public int getAllTotal() {
		return allTotal;
	}

	public void setAllTotal(int allTotal) {
		this.allTotal = allTotal;
	}

	public int getAllTrans() {
		return allTrans;
	}

	public void setAllTrans(int allTrans) {
		this.allTrans = allTrans;
	}

	public int getAllForeignWords() {
		return allForeignWords;
	}

	public void setAllForeignWords(int allForeignWords) {
		this.allForeignWords = allForeignWords;
	}

	public int getAllTransWords() {
		return allTransWords;
	}

	public void setAllTransWords(int allTransWords) {
		this.allTransWords = allTransWords;
	}

	public double getPercent() {
		return percent;
	}

	public void setPercent(double percent) {
		this.percent = percent;
	}

	public int[] getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(int[] encryptionKey) {
		this.encryptionKey = encryptionKey;
	}

	public Map<String, FileMeta> getFileMap() {
		return fileMap;
	}

	public void setFileMap(Map<String, FileMeta> fileMap) {
		this.fileMap = fileMap;
	}

	public List<TreeModel> getTree() {
		return tree;
	}

	public void setTree(List<TreeModel> tree) {
		this.tree = tree;
	}

	@JsonIgnore
	public void recount() {
		int allTrans = 0;
		int allTotal = 0;
		int foreignWords = 0;
		int transWords = 0;
		if (getFileMap() != null) {
			for (FileMeta fileMeta : getFileMap().values()) {
				allTrans += fileMeta.getTrans();
				allTotal += fileMeta.getTotal();
				foreignWords += fileMeta.getForeignWords();
				transWords += fileMeta.getTransWords();
			}
		}
		setAllForeignWords(foreignWords);
		setAllTransWords(transWords);
		setAllTotal(allTotal);
		setAllTrans(allTrans);
		if (allTotal != 0) {
			setPercent(new BigDecimal(allTrans * 100).divide(new BigDecimal(allTotal), 2, BigDecimal.ROUND_HALF_EVEN)
					.doubleValue());
		}
	}

	@JsonInclude(value = Include.NON_NULL)
	public static class TreeModel implements Serializable {
		private static final long serialVersionUID = 1640559576931592869L;
		@JsonIgnore
		private int id;
		@JsonIgnore
		private int parentId;
		@JsonProperty("f")
		private String fileName;
		@JsonProperty("s")
		private String showName;
		@JsonProperty("n")
		private List<TreeModel> nodes;

		public TreeModel() {
			super();
		}

		public TreeModel(String fileName) {
			super();
			this.fileName = fileName;
		}

		public TreeModel(int id, int parentId, String fileName) {
			super();
			this.id = id;
			this.parentId = parentId;
			this.fileName = fileName;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public int getParentId() {
			return parentId;
		}

		public void setParentId(int parentId) {
			this.parentId = parentId;
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getShowName() {
			return showName;
		}

		public void setShowName(String showName) {
			this.showName = showName;
		}

		public List<TreeModel> getNodes() {
			return nodes;
		}

		public void setNodes(List<TreeModel> nodes) {
			this.nodes = nodes;
		}

	}

}
