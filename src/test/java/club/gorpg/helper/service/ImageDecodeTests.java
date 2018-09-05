package club.gorpg.helper.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ImageDecodeTests {
	public static void main(String[] args) throws IOException {
		String path = "E:\\mv\\1\\foreign\\www\\img\\animations\\ArrowSpecial.rpgmvp";
		String path1 = "E:\\mv\\1\\foreign\\www\\img\\animations\\ArrowSpecial.png";
		int x = 99;
		System.out.println(Integer.toHexString(x));
		String strKey = "d14c2267d848abeb81fd590f371d39bd";
		String[] strArr = new String[16];
		int[] byteArr = new int[16];
		for (int i = 0; i < 16; i++) {
			String k = strKey.substring(i * 2, i * 2 + 2);
			short b = Short.parseShort(k, 16);

			strArr[i] = k;
			byteArr[i] = b;
		}
		InputStream in = new BufferedInputStream(new FileInputStream(path));
		OutputStream out = new BufferedOutputStream(new FileOutputStream(path1, false));
		int index = 0;

		while (true) {
			int i = in.read();
			if (i == -1) {
				break;
			}
			if (index >= 16 && index < 32) {
				out.write(i ^ byteArr[index % 16]);
			} else if (index >= 32) {
				out.write(i);
			}
			index++;
		}
		in.close();
		out.flush();
		out.close();
		System.out.println("OK");

	}
}
