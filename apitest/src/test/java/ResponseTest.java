import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import model.Owner;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.get;

public class ResponseTest {
    final static String REQUEST_URL = "https://api.stackexchange.com/2.2/answers?" +
            "site=stackoverflow&page=1&pagesize=10&order=desc&sort=activity&filter=default";
    private final static int CORRECT_STATUS_CODE = 200;
    private final static int CORRECT_SIZE = 10;
    private String rootPath = "items";
    private String pathOwner = "owner";
    private String pathUserId = "owner.user_id";
    private String pathDisplayName = "owner.display_name";
    private String pathOwnerLink = "owner.link";
    private Response response;
    private JsonPath jsonPath;
    private List<Owner> ownersList;
    private static Logger log = Logger.getLogger(ResponseTest.class);

    @BeforeClass
    public void statusCodeTest() {
        response = get(REQUEST_URL);
        log.info("Run assert. Verify status code");
        Assert.assertEquals(CORRECT_STATUS_CODE, response.getStatusCode());
    }

    @Test
    public void sizeOfRecordsTest() {
        String json = response.asString();
        jsonPath = new JsonPath(json).setRootPath(rootPath);
        List<String> records = jsonPath.get(pathOwner);
        log.info("Run assert. Verify amount of records");
        Assert.assertTrue(records.size() <= CORRECT_SIZE);
    }

    @Test(dependsOnMethods = {"sizeOfRecordsTest"})
    public void objectListTest() {
        List<Integer> userIdList = jsonPath.get(pathUserId);
        List<String> linkList = jsonPath.get(pathOwnerLink);
        List<String> displayName = jsonPath.get(pathDisplayName);
        ownersList = new ArrayList<>();
        log.info("Run assert. Verify each element in list of owners");
        for (int i = 0; i < userIdList.size(); i++) {
            Owner owner = new Owner();
            owner.setUserId(userIdList.get(i));
            owner.setOwnerLink(linkList.get(i));
            owner.setDisplayName(displayName.get(i));
            ownersList.add(owner);
            Assert.assertNotNull(owner);
        }
    }

    @Test(dependsOnMethods = {"objectListTest"})
    public void linkContainsTest() {
        log.info("Run asserts. Each owner link is formed from display_name and user_id");
        for (Owner owner : ownersList) {
            log.info("Run assert. The owner link [" + ownersList.indexOf(owner) + "] contains display_name");
            String[] strings = owner.getDisplayName().split("(?U)[^\\p{L}\\p{N}]+");
            for (String str : strings) {
                boolean isContainsInLink = StringUtils.containsIgnoreCase(owner.getOwnerLink(), str);
                Assert.assertTrue(isContainsInLink, owner.getDisplayName());
            }
            int id = owner.getUserId();
            log.info("Run assert. The owner link [" + ownersList.indexOf(owner) + "] contains user_id");
            Assert.assertTrue(owner.getOwnerLink().contains(Integer.toString(id)));
        }
    }
}
