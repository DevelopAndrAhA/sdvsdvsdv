package controllers;


import neural_network.FaceRecognizer;
import neural_network.models.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import service.MyServiceClass;

/*import javax.annotation.PostConstruct;*/
import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping(value = "api")
public class MainController {
	@Autowired
	MyServiceClass service;
	@Autowired
	ServletContext servletContext;

	List<FullFaceFeatures> fullFaceFeatures = new ArrayList<FullFaceFeatures>();
	FaceRecognizer faceRecognizer = new FaceRecognizer();



	@ResponseBody
	@RequestMapping(method = RequestMethod.GET)
	public String getData(){
		return "hello";
	}

	@ResponseBody
	@RequestMapping(value = "new_face",method = RequestMethod.POST)
	public Object add(
			@RequestParam("crop") MultipartFile crop,
			@RequestParam("largePohto") MultipartFile largePohto,
			@RequestParam("username")String username,
			@RequestParam("lng")String lng,
			@RequestParam("lat")String lat) {
		String photoName = null;
		Date date = new Date();

		long currTime = date.getYear()+date.getDay()+date.getMonth()+date.getTime();
		File theDir = new File(File.separator+"images");
		if (!theDir.exists()){
			theDir.mkdir();
		}
		try{
			byte[] bytes = largePohto.getBytes();
			String rootPath = theDir.getAbsolutePath();
			photoName = currTime+".jpg";
			File serverFile = new File(rootPath+ File.separator +  photoName);
			FileOutputStream fileOutputStream = new FileOutputStream(serverFile);
			BufferedOutputStream stream = new BufferedOutputStream(fileOutputStream);
			stream.write(bytes);
			fileOutputStream.close();
			stream.flush();
			stream.close();


			BufferedImage bufferedImage = resize(largePohto);
			File outputfile = new File(rootPath+File.separator+currTime+"_SMALL"+".jpg");
			try{
				ImageIO.write(bufferedImage, "jpg", outputfile);
			}catch (Exception e){e.printStackTrace();}


			FaceFeatures faceFeatures = faceRecognizer.addNew(crop);

			FullFaceFeatures features = new FullFaceFeatures(username);
			features.setFaceFeatures(1,faceFeatures);
			features.setIdentifier(new Date().getSeconds());
			features.setPhotoName(photoName);
			faceFeatures.setLng(Double.parseDouble(lng));
			faceFeatures.setLat(Double.parseDouble(lat));
			faceFeatures.setFullFaceFeatures(features);

			fullFaceFeatures.add(features);
			service.save(features);
			ResultContainer resultContainer = new ResultContainer();
			resultContainer.setStatus(200);
			resultContainer.setDesc("success");
			return resultContainer;

		}catch (Exception e){e.printStackTrace();}



		return "errrror";


	}


	@ResponseBody
	@RequestMapping(value = "search",method = RequestMethod.POST)
	public Object search(@RequestParam("file") MultipartFile multipartFile,@RequestParam("percent")float percent,@RequestParam("inpDate")String inpDate) {
		java.sql.Date date = java.sql.Date.valueOf(inpDate);
		List<Prediction> prediction = faceRecognizer.searchFromPool(multipartFile,fullFaceFeatures,percent,date);
		if(prediction!=null){
			return prediction;
		}
		ResultContainer resultContainer = new ResultContainer();
		resultContainer.setStatus(404);
		resultContainer.setDesc("Нет данных");
		return resultContainer;
	}


	@ResponseBody
	@RequestMapping(value = "image", method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public  byte[]  getPhoto(@RequestParam("imgname")String imgname){
		try {
			String rootPath = File.separator+"images"+File.separator+imgname;

			InputStream in = null;
			File f = new File(rootPath);
			in = new FileInputStream(f);
			return IOUtils.toByteArray(in);
		}catch (Exception e){e.printStackTrace();}
		return null;
	}




	@ResponseBody
	@RequestMapping(value = "getInitData",method = RequestMethod.GET)
	public Object getFirstData(@RequestParam("lat")String lat,@RequestParam("lng")String lng){
		List<ResponseModel>list = service.getFullFeatures(Double.parseDouble(lat),Double.parseDouble(lng));
		return list;
	}



	public BufferedImage resize(MultipartFile photo) {


		BufferedImage bufferedImage = null;
		try {
			byte [] bytes = photo.getBytes();
			InputStream is = new ByteArrayInputStream(bytes);
			bufferedImage = ImageIO.read(is);
		}catch (Exception e){}

		bufferedImage = resize(bufferedImage,bufferedImage.getWidth()/5,bufferedImage.getHeight()/5);
		return bufferedImage;
	}
	public static BufferedImage resize(BufferedImage img, int newW, int newH) {
		Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
		BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_RGB);

		Graphics2D g2d = dimg.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return dimg;
	}

	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		fullFaceFeatures = service.getFullFeatures();
	}







}