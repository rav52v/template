package main.java.utils;

import java.util.HashMap;
import java.util.Map;

public class Buffer {
  public static Buffer instance;
  private Map<Object, Object> buffer;

  private Buffer() {
    buffer = new HashMap<>();
  }

  public static Buffer getInstance() {
    if (instance == null)
      instance = new Buffer();
    return instance;
  }

  public void put(Object key, Object value) {
    buffer.put(key, value);
  }

  public Object get(Object key) {
    return buffer.get(key);
  }

  public boolean containsKey(Object key) {
    return buffer.containsKey(key);
  }
}
