package ch.hsr.intte.servermp.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class AbstractService<Entity> {

	private final Lock lock = new ReentrantLock();

	private String filename;

	private Map<String, Entity> entitiesById;

	protected AbstractService(String filename) {
		this.filename = filename;

		entitiesById = loadEntitiesById();
	}

	private Map<String, Entity> loadEntitiesById() {
		try (Scanner scanner = new Scanner(openFile())) {
			return tryLoadEntitiesById(scanner);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private Map<String, Entity> tryLoadEntitiesById(Scanner scanner) {
		Map<String, Entity> entitiesById = new HashMap<>();
		while (scanner.hasNextLine())
			try {
				Entity entity = convert(scanner.nextLine());
				entitiesById.put(getId(entity), entity);
			} catch (RuntimeException exception) {
				System.err.println(exception);
			}

		return entitiesById;
	}

	public void persist(Entity entity) {
		try {
			lock.lock();
			save(entity);
			entitiesById.put(getId(entity), entity);
		} finally {
			lock.unlock();
		}
	}

	public Entity findById(String id) {
		try {
			lock.lock();
			return entitiesById.get(id);
		} finally {
			lock.unlock();
		}
	}

	public Collection<Entity> findAll() {
		try {
			lock.lock();
			return entitiesById.values();
		} finally {
			lock.unlock();
		}
	}

	private void save(Entity entity) {
		try (FileWriter writer = new FileWriter(openFile(), true)) {
			trySave(entity, writer);
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	private void trySave(Entity entity, FileWriter writer) throws IOException {
		writer.write(convert(entity));
		writer.write('\n');
	}

	private File openFile() throws IOException {
		File file = new File(filename);
		if (!file.exists())
			file.createNewFile();

		return file;
	}

	abstract String getId(Entity entity);

	abstract Entity convert(String line);

	abstract String convert(Entity entity);

}
