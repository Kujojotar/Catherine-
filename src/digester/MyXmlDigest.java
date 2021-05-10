package digester;

import core.connector.MyConnectorConfig;
import core.connector.SimpleConnectorConfig;
import core.container.*;
import core.server.MyServerConfig;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.util.Stack;

/**
 * @author james
 * 一个用于解析xml的类
 * 采用了sax的解析方式
 */
public class MyXmlDigest extends DefaultHandler {
    private Stack<String> tags;
    private String executeTag;
    private Stack<ContainerConfig> configs;
    public  MyServerConfig myServerConfig;
    private ContainerConfig config;

    public MyXmlDigest(){
        super();
        tags=new Stack<>();
        configs=new Stack<>();
        executeTag=null;
        myServerConfig=null;
    }

    @Override
    public void startDocument() throws SAXException {
        tags.removeAllElements();
        configs.removeAllElements();
        executeTag=null;
        myServerConfig=null;
        super.startDocument();
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if("Server".equals(qName)||"Engine".equals(qName)||"Host".equals(qName)||"Context".equals(qName)){
            executeTag=qName;
            if(config!=null){
                configs.push(config);
            }
        }
        int sign=getIntSign(executeTag);
        switch(sign){
            case 1:
                if("Server".equals(qName)){
                    MyServerConfig serverConfig=new MyServerConfig();
                    String port=attributes.getValue("port");
                    serverConfig.setServerPort(Integer.valueOf(port));
                    serverConfig.setShutdown(attributes.getValue("shutdown"));
                    config=serverConfig;
                }else if("Service".equals(qName)){
                    ((MyServerConfig)config).setServiceName(attributes.getValue("name"));
                }else if("Connector".equals(qName)){
                    MyConnectorConfig connectorConfig=new MyConnectorConfig();
                    connectorConfig.setPort(Integer.valueOf(attributes.getValue("port")));
                    connectorConfig.setProtocol(attributes.getValue("protocol"));
                    String timout=attributes.getValue("connectionTimeOut");
                    if(timout!=null){
                        connectorConfig.setConnectionTimeout(Integer.valueOf(timout));
                    }
                    String redirectPort=attributes.getValue("redirectPort");
                    if(redirectPort!=null){
                        connectorConfig.setRedirectPort(Integer.valueOf(redirectPort));
                    }
                    ((MyServerConfig)config).addConfig(connectorConfig);
                }else{

                }
                break;
            case 2:
                if("Engine".equals(qName)){
                    MyEngineConfig engineConfig=new MyEngineConfig();
                    engineConfig.setName(attributes.getValue("name"));
                    engineConfig.setDefaultHost("defaultHost");
                    config=engineConfig;
                }
                break;
            case 3:
                if("Host".equals(qName)){
                    MyHostConfig myHostConfig=new MyHostConfig();
                    myHostConfig.setName(attributes.getValue("name"));
                    myHostConfig.setAppBase(attributes.getValue("appBase"));
                    myHostConfig.setAutoDeploy(Boolean.valueOf(attributes.getValue("autoDeploy")));
                    myHostConfig.setUnPackWARS(Boolean.valueOf(attributes.getValue("unpackWARs")));
                    config=myHostConfig;
                }
                break;
            case 4:
                if("Context".equals(qName)){
                    MyContextConfig myContextConfig=new MyContextConfig();
                    myContextConfig.setPath(attributes.getValue("path"));
                    myContextConfig.setDocBase(attributes.getValue("docBase"));
                    config=myContextConfig;
                }
            default:
                ;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        int sign=getIntSign(qName);
        ContainerConfig newConfig=null;
        switch(sign){
            case 1:
                myServerConfig=(MyServerConfig)config;
                break;
            case 2:
                newConfig=configs.pop();
                ((MyServerConfig)newConfig).setEngineConfig((MyEngineConfig)config);
                config=newConfig;
                break;
            case 3:
                newConfig=configs.pop();
                ((MyEngineConfig)newConfig).addConfig((MyHostConfig)config);
                config=newConfig;
                break;
            case 4:
                newConfig=configs.pop();
                ((MyHostConfig)newConfig).addConfig((MyContextConfig)config);
                config=newConfig;
                break;
            default:
                ;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);
    }

    public int getIntSign(String str){
        if("Server".equals(str)){
            return 1;
        }else if("Engine".equals(str)){
            return 2;
        }else if("Host".equals(str)){
            return 3;
        }else if("Context".equals(str)){
            return 4;
        }else{
            return 0;
        }
    }
}


class digeTest{
    public static void main(String[] args)throws Exception {
        SAXParserFactory factory=SAXParserFactory.newInstance();

        SAXParser parser= factory.newSAXParser();

        XMLReader reader= parser.getXMLReader();

        MyXmlDigest digest=new MyXmlDigest();
        reader.setContentHandler(digest);
        reader.parse("C:\\Users\\Jonny\\Desktop\\Catherine\\Webroot\\config\\server.xml");

        System.out.println(digest.myServerConfig);
    }
}