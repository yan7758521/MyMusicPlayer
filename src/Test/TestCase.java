package Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.show.api.Constants;
import com.show.api.ShowApiRequest;

public class TestCase {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SimpleDateFormat df = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		   String res=new ShowApiRequest("http://route.showapi.com/213-1","2282","9e8a852ee9014379bdea3a7af106d85a")
		   .addTextPara("showapi_timestamp",df.format(new Date()))
		   .addTextPara("keyword", "ÖÜ½ÜÂ×²Êºç")
		 
		   .post();
		System.out.println(res);

	}

}
