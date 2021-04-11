package Core.session;

public class FacadeHttpSession implements HttpSession {
    private Session session;

    public FacadeHttpSession(Session session){
        this.session=session;
    }

}
