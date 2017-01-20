package com.movesol.jparsec;

import java.util.Comparator;
import java.util.Locale;

enum StringCase implements Comparator<String> {
  CASE_SENSITIVE {
    @Override public int compare(String a, String b) {
      return a.compareTo(b);
    }
    @Override String toKey(String k) {
      return k;
    }
  },
  CASE_INSENSITIVE {
    @Override public int compare(String a, String b) {
      return a.compareToIgnoreCase(b);
    }
    @Override public String toKey(String k) {
      return k.toLowerCase(Locale.ENGLISH);
    }
  }
  ;

  abstract String toKey(String k);

  final <T> com.movesol.jparsec.functors.Map<String, T> toMap(
      final java.util.Map<String, T> m) {
    return new com.movesol.jparsec.functors.Map<String,T>() {
      @Override public T map(String key) {
        return m.get(toKey(key));
      }
    };
  }
}