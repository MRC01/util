/* Copyright 2012 by Michael R. Clements
 * This source code is the intellectual property of the author.
 * This is not open source. All rights reserved.
*/
package com.mrc.util;

import java.io.*;

import android.media.*;
import android.content.res.*;

/* This class facilitates the use of sound effects.
 * It plays long-running sound effects using the Android streaming audio MediaPlayer.
*/
public class SoundLong {
	static final float				VOL_DEFAULT = 1F,
									VOL_MUTE = 0F;

	protected MediaPlayer			player;
	protected FileDescriptor		fDes;
	protected float					vol;

	public SoundLong(String _fName, float _vol) throws IOException {
		init(_fName, _vol, false, null);
	}
	
	public SoundLong(String _fName, float _vol, boolean _loop) throws IOException {
		init(_fName, _vol, _loop, null);
	}
	
	public SoundLong(String _fName, AssetManager am) throws IOException {
		init(_fName, VOL_DEFAULT, false, am);
	}
	
	public SoundLong(String _fName, float _vol, AssetManager am) throws IOException {
		init(_fName, _vol, false, am);
	}
	
	public SoundLong(String _fName, float _vol, boolean _loop, AssetManager am) throws IOException {
		init(_fName, _vol, _loop, am);
	}
	
	protected void init(String _fName, float _vol, boolean _loop, AssetManager am) throws IOException {
		vol = _vol;
		player = new MediaPlayer();
		if(am == null)
			fDes = new FileInputStream(_fName).getFD();
		else
			fDes = am.openFd(_fName).getFileDescriptor();
		player.setDataSource(fDes);
		player.prepare();
		player.setLooping(_loop);
		player.setVolume(vol, vol);
	}

	public void play() {
		if(!player.isPlaying())
			player.start();
	}

	public void stop() {
		if(player.isPlaying())
			player.pause();
		setPos(0);
	}

	public void pause() {
		if(player.isPlaying())
			player.pause();
	}

	public void setPos(int pos) {
		player.seekTo(pos);
	}

	public int getPos() {
		return player.getCurrentPosition();
	}

	public void mute() {
		player.setVolume(VOL_MUTE,  VOL_MUTE);
	}

	public void unMute() {
		player.setVolume(vol, vol);
	}

	public void close() {
		player.stop();
		player.release();
	}
}
