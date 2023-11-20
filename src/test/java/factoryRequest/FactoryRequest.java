package factoryRequest;

public class FactoryRequest {
    public static IRequest make(String type){
        IRequest request;
        switch (type.toLowerCase()){
            case "post":
                request = new RequestPOST();
                break;
            case "get":
                request = new RequestGET();
                break;
            case "put":
                request = new RequestPUT();
                break;
            case "delete":
                request = new RequestDELETE();
                break;
            default:
                request = null;
                break;
        }
        return request;
    }
}
