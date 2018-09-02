package club.gorpg.helper.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public class GameListMeta implements Serializable {
	private static final long serialVersionUID = -7609263459961169337L;

	private Map<String, SingleGameMeta> game;

	private List<String> sort;

	public Map<String, SingleGameMeta> getGame() {
		return game;
	}

	public void setGame(Map<String, SingleGameMeta> game) {
		this.game = game;
	}

	public List<String> getSort() {
		return sort;
	}

	public void setSort(List<String> sort) {
		this.sort = sort;
	}

	public static class SingleGameMeta implements Serializable {
		private static final long serialVersionUID = 6094884155610900836L;

		private String cname;
		private String fname;
		private String id;
		private double percent;

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

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public double getPercent() {
			return percent;
		}

		public void setPercent(double percent) {
			this.percent = percent;
		}

	}
}
