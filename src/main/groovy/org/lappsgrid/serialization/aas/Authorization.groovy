package org.lappsgrid.serialization.aas
/**
 * @author Keith Suderman
 */
class Authorization {
    String username
    String resourceId
    Token token;
    List<License> restrictions

//    public Authorization() {
//    }
//
//
//    public Authorization(Authorization auth) {
//        this()
//        assignFrom(auth)
//    }
//
//    private void assignFrom(Authorization proxy) {
//        this.username = proxy.username
//        this.resourceId = proxy.resourceId
//        this.restrictions = []
//        this.restrictions.addAll(proxy.restrictions)
//    }
//
//    String toJson() {
//        objectMapper.disable(SerializationFeature.INDENT_OUTPUT)
//        return objectMapper.writeValueAsString(this)
//    }
//
//    String toPrettyJson() {
//        objectMapper.enable(SerializationFeature.INDENT_OUTPUT)
//        return objectMapper.writeValueAsString(this)
//    }
//
//    /** Calls toPrettyJson() */
//    String toString() {
//        return toJson()
//    }
//
}
