package com.letv.android.client.skin.videoview.live;

import android.content.Context;

import com.lecloud.skin.videoview.live.UIActionLiveVideoView;
import com.letv.android.client.cp.sdk.player.live.CPActionLivePlayer;

public class UICPActionLiveVideoView extends UIActionLiveVideoView {

	public UICPActionLiveVideoView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

    @Override
    protected void initPlayer() {
        player = new CPActionLivePlayer(context);
    }
}
