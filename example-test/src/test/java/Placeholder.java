public class Placeholder {
    private final int userId;
    private final int id;
    private final String title;
    private final String body;

    public int getUserId() {
        return userId;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }


    public static Placeholder.PlaceholderBuilder builder() {
        return new Placeholder.PlaceholderBuilder();
    }

    public Placeholder(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public static class PlaceholderBuilder {
        private int userId;
        private int id;
        private String title;
        private String body;

        PlaceholderBuilder() {
        }

        public Placeholder.PlaceholderBuilder userId(final int userId) {
            this.userId = userId;
            return this;
        }


        public Placeholder.PlaceholderBuilder id(final int id) {
            this.id = id;
            return this;
        }

        public Placeholder.PlaceholderBuilder title(final String title) {
            this.title = title;
            return this;
        }

        public Placeholder.PlaceholderBuilder body(final String body) {
            this.body = body;
            return this;
        }


        public Placeholder build() {
            return new Placeholder(this.userId, this.id, this.title, this.body);
        }

        public String toString() {
            return "Person.PersonBuilder(userId=" + this.userId + ", id=" + this.id + ", title=" + this.title + ", body=" + this.body + ")";
        }
    }
}