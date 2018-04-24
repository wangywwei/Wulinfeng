package com.hxwl.newwlf.home.home.follow;

public class Friend implements Comparable<Friend> {

	private String mPinYin;
	private String playerId;
	private String playerImage;
	private String playerName;
	private String playerClub;
	private String weightLevel;
	private int userIsFollow;

	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayerImage() {
		return playerImage;
	}

	public void setPlayerImage(String playerImage) {
		this.playerImage = playerImage;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public String getPlayerClub() {
		return playerClub;
	}

	public void setPlayerClub(String playerClub) {
		this.playerClub = playerClub;
	}

	public String getWeightLevel() {
		return weightLevel;
	}

	public void setWeightLevel(String weightLevel) {
		this.weightLevel = weightLevel;
	}

	public int getUserIsFollow() {
		return userIsFollow;
	}

	public void setUserIsFollow(int userIsFollow) {
		this.userIsFollow = userIsFollow;
	}

	public Friend(String playerId, String playerImage, String playerName, String playerClub, String weightLevel, int userIsFollow) {
		super();
		this.playerId = playerId;
		this.playerImage = playerImage;
		this.playerName = playerName;
		this.playerClub = playerClub;
		this.weightLevel = weightLevel;
		this.userIsFollow = userIsFollow;
		setmPinYin(PinYinUtils.getPinYin(playerName));
	}

	public String getmPinYin() {
		return mPinYin;
	}

	public void setmPinYin(String mPinYin) {
		this.mPinYin = mPinYin;
	}

	// 拼音字母(字典顺序 )
	@Override
	public int compareTo(Friend another) {
		return getmPinYin().compareTo(another.getmPinYin());
	}
}
