package org.anc.lapps.serialization

/**
 * Holds information for the 'contains' sections of a {@link org.anc.lapps.serialization.ProcessingStep}'s
 * metadata section.
 * <p/>
 * The <em>contains</em> metadata allows pipelines (planners or composers) to determine the
 * contents of a ProcessingStep without having to traverse the contents of the
 * <em>annotations</em> list.
 *
 * @author Keith Suderman
 */
class Contains {
    /**
     * The URL of the processor that produced the annotations.
     */
    String url;

    /**
     * The name of the processors that produced the annotations.  For Java
     * processors this will be the fully qualified class name of the processor
     * including version information.
     */
    String producer;

    /**
     * The annotation type.
     */
    String type;
}
