package by.bsuir.iit.aipos.service.constant;

public enum HttpMethod {

    GET {
        @Override
        public String getEntityBody() {
            return "";
        }
    },

    HEAD {
        @Override
        public String getEntityBody() {
            return "";
        }
    },

    POST {
        @Override
        public String getEntityBody() {
            return "login=alshund&password=xxx";
        }
    };

    public abstract String getEntityBody();
}
