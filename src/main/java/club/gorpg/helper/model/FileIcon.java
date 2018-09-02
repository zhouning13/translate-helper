package club.gorpg.helper.model;

public enum FileIcon {
	//
	actor("fa fa-user"),
	//
	animation("fas fa-film"),
	//
	armor("fas fa-shield-alt"),
	//
	classes("fa fa-male"),
	//
	event("fa fa-clipboard-list"),
	//
	enemy("fa fa-user-secret"),
	//
	item("fa fa-gift"),
	//
	map("fas fa-map-marked-alt"),
	//
	mapInfo("fa fa-globe"),
	//
	skill("fa fa-eye"),
	//
	state("fa fa-fire"),
	//
	system("fa fa-cogs"),
	//
	tileset("fa fa-building"),
	//
	troop("fa fa-users"),
	//
	weapon("fa fa-gavel");
	private final String icon;

	private FileIcon(String icon) {
		this.icon = icon;
	}

	public String getIcon() {
		return icon;
	}

}
