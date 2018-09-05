package club.gorpg.helper.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import club.gorpg.helper.model.FileMeta;
import club.gorpg.helper.model.GameMeta;
import club.gorpg.helper.service.IFileTemplateService;

public abstract class AbstractDecryptFileTemplateServiceImpl implements IFileTemplateService {

	protected abstract String getAcceptableSurffix();// ".rpgmvp"

	protected abstract String getChangedSurffix();// ".png"

	public List<FileMeta> handle(GameMeta gm, String fileName, Path path, Path chinesePath) {
		return null;
	}

	public void aferHandle(GameMeta gm, String fileName, Path path, Path chinesePath) {
		if (!path.toString().endsWith(getAcceptableSurffix())) {
			return;
		}

		int[] byteArr = gm.getEncryptionKey();
		if (byteArr == null || byteArr.length == 0) {
			return;
		}
		String name = path.getFileName().toString();
		String newName = name.substring(0, name.lastIndexOf(".")) + getChangedSurffix();
		Path newPath = chinesePath.resolveSibling(newName);

		try (InputStream in = new BufferedInputStream(new FileInputStream(path.toFile()));
				OutputStream out = new BufferedOutputStream(new FileOutputStream(newPath.toFile(), false))) {
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
			out.flush();
			Files.delete(chinesePath);
		} catch (IOException e) {
			// DO NOTHING
		}
	}
}
