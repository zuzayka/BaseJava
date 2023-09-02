package com.urise.webapp;

public class LazySingleton {
    private static volatile LazySingleton INSTANCE;
    static int i;

    private LazySingleton() {

    }

    private static class LazySingletonHolder {
        private static final LazySingleton INSTANCE = new LazySingleton();

        public static LazySingleton getInstance() {
            return LazySingletonHolder.INSTANCE;
        }
    }

//    public static LazySingleton getInstance() {
//        if (INSTANCE == null) {
//            synchronized (LazySingleton.class) {
//                if (INSTANCE == null) {
//                    i = 13;                         //   если не делать INSTANCE volatile при return INSTANCE;
//                                                    //   может не произойти присвоение значения 13 переменной i
//                                                    //   вместо volatile можно использовать final c таким же эффектом
//                    INSTANCE = new LazySingleton();
//                }
//            }
//        }
//        return INSTANCE;
//    }
}
