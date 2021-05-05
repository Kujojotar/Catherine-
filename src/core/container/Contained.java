package core.container;

/**
 * @author james
 */
public interface Contained {
    /**
     * nmd这就叫javadoc?
     * @return
     */
    public Container getContainer();

    /**
     * 老子想把这个插件卸了
     * @param container
     */
    public void setContainer(Container container);
}
