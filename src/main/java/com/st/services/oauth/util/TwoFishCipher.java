package com.st.services.oauth.util;

import java.util.logging.Logger;

import java.security.InvalidKeyException;

public class TwoFishCipher {
	
	/*

	private static final Logger logger = Logger.getLogger(TwoFishCipher.class
			.getName());
	
	private static final String KEY = "q(74Khd^30*5%21*dk*CiE38^re_0Qh1";

	private static final int BLOCK_SIZE = 16;
	private static final boolean useTwofish = false;
	
	 public static String encrypt(String in){
	
	        return encrypt(in, KEY);
	 }
	 

    public static String decrypt(String in) {

        return decrypt(in, KEY);
    }

	public static String encrypt(String in, String keyText) {

		Object key = null;

		try {
			key = Twofish_Algorithm.makeKey(keyText.getBytes());
		} catch (InvalidKeyException e) {
			logger.warning(e.toString());
		}

		int padded_length = 0;

		if ((in.length() % 16) == 0) {
			padded_length = in.length();
		} else {
			padded_length = in.length() + (BLOCK_SIZE - (in.length() % 16));
		}

		byte[] b = new byte[padded_length];
		System.arraycopy(in.getBytes(), 0, b, 0, in.length());

		byte[] out = new byte[padded_length];

		for (int i = 0; i < padded_length; i += BLOCK_SIZE) {
			System.arraycopy(Twofish_Algorithm.blockEncrypt(b, i, key), 0, out,
					i, BLOCK_SIZE);
		}

		return StringUtil.bytesToHexString(out);
	}

	public static String decrypt(String in, String keyText) {

		Object key = null;

		try {
			key = Twofish_Algorithm.makeKey(keyText.getBytes());
		} catch (InvalidKeyException e) {
			logger.warning(e.toString());
		}

		byte[] b = StringUtil.hexStringToBytes(in);
		byte[] out = new byte[b.length];

		for (int i = 0; i < b.length; i += BLOCK_SIZE) {
			System.arraycopy(Twofish_Algorithm.blockDecrypt(b, i, key), 0, out,
					i, BLOCK_SIZE);
		}

		return (new String(StringUtil.getAsciiBytes(new String(out)))).trim();
	}
	
	 public static BlobB encryptPasswd(String in) {
	        if ((in == null) || (in.length() == 0)) {
	            return null;
	        }

	        // for now use Towfish
	       
	            return new BlobB("tf", "theKey", encrypt(in));
	       
	 }
	 */

}
