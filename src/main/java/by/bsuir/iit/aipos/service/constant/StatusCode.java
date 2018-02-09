package by.bsuir.iit.aipos.service.constant;

public enum StatusCode {

    OK {
        @Override
        public int getCode() {
            return 200;
        }
    },

    CREATED {
        @Override
        public int getCode() {
            return 201;
        }
    },

    ACCEPTED {
        @Override
        public int getCode() {
            return 202;
        }
    },

    NO_CONTENT {
        @Override
        public int getCode() {
            return 204;
        }
    },

    MOVED_PERMANENTLY {
        @Override
        public int getCode() {
            return 301;
        }
    },

    MOVED_TEMPORARILY {
        @Override
        public int getCode() {
            return 302;
        }
    },

    NOT_MODIFIED {
        @Override
        public int getCode() {
            return 304;
        }
    },

    BAD_REQUEST {
        @Override
        public int getCode() {
            return 400;
        }
    },

    UNAUTHORIZED {
        @Override
        public int getCode() {
            return 401;
        }
    },

    FORBIDDEN {
        @Override
        public int getCode() {
            return 403;
        }
    },

    NOT_FOUND {
        @Override
        public int getCode() {
            return 404;
        }
    },

    INTERNAL_SERVER_ERROR {
        @Override
        public int getCode() {
            return 500;
        }
    },

    NOT_IMPLEMENTED {
        @Override
        public int getCode() {
            return 501;
        }
    },

    BAD_GATEWAY {
        @Override
        public int getCode() {
            return 502;
        }
    },

    SERVICE_UNAVAILABLE {
        @Override
        public int getCode() {
            return 503;
        }
    };

    public abstract int getCode();

    public static String getRegPattern() {
        return "httpPatterns.responseStatusLineReg";
    }
    public static int getStatusNameGroup() {
        return 3;
    }
}
