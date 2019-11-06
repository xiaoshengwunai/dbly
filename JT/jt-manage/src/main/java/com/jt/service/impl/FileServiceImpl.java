package com.jt.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;

/**
 * 国庆假期后:
 * 		增加了
 */
@Service
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {

	//定义真实路径
	@Value("${image.localDirPath}")
	private String  localDirPath;
	//定义虚拟路径
	@Value("${image.urlDirPath}")
	private String urlDirPath;
	
	/**
	 * 实现思路:
	 * 		1.校验图片类型jpg/jpeg/png/gif...
	 * 		2.校验是否为恶意程序(木马.exe.jpg,伪装)
	 * 		3.文件分类存储(按类型不太容易)
	 * 			文件.hash=32位~~~2位?
	 * 			时间,每一天一个文件夹
	 * 		4.防止文件重名
	 * 			自定义文件名称 uuid.类型
	 */
	@Override
	public ImageVO upload(MultipartFile uploadFile) {
		// 1.获取图片名称 假设为abc.jpg
		String fileName = uploadFile.getOriginalFilename();
//		System.out.println("fileName###" + fileName); //测试正常
		// 2.执行(利用正则)
		fileName = fileName.toLowerCase();	//管你什么鬼,来我这都给我跪着...小写
		if(!fileName.matches("^.+\\.(jpg|png|gif)$")){	//忽略(.)之前的文件名,只看.之后
			System.out.println("文件格式不符合规范啊!");
			return ImageVO.fail();
		}
		System.out.println("格式校验成功!");
		// 3.校验恶意程序 利用图片文件特有属性:拥有高度\宽度\px像素
		try {
			// BUfferedImage是专门处理图片的对象,不能处理别的文件
			BufferedImage bImage = ImageIO.read(uploadFile.getInputStream());
			int width = bImage.getWidth();
			int height = bImage.getHeight();
			// 校验这两个数的真实性
			if (width == 0 || height == 0) return ImageVO.fail();
			System.out.println("这真的是图片鸭!");
			// 4.实现分文件存储. yyyy/MM/dd/
			String dateDir = new SimpleDateFormat("yyyy/MM/dd/").format(new Date());// 特意多一个斜杠
			// 要这种格式的   D:/1....jt-images/2019/09/30
			String dirFilePath = localDirPath + dateDir;
//			System.out.println(dirFilePath);	//看能否如愿
			File dirFile = new File(dirFilePath);
			if (!dirFile.exists()) {	// 如果目录不存在,就创建一个
				dirFile.mkdirs();
			}
			
			// 5.动态生成文件名称uuid +文件后缀
			String uuid = UUID.randomUUID().toString();
			String fileType = fileName.substring(fileName.lastIndexOf("."));// 从最后一个"."符号开始截取(substring)
			String realFileName = uuid + fileType;
			
			// E:xxxxx/yyyy/MM/dd/uuid.jgp
			String realFilePath = dirFilePath + realFileName;
			uploadFile.transferTo(new File(realFilePath));
			
			// 6.实现数据的回显
			String url = urlDirPath + dateDir +realFileName;
			ImageVO imageVO = new ImageVO(0, url, width, height);
			System.out.println("业务层返回结果为:" + imageVO);
			return imageVO;
			
		} catch (Exception e) {
			System.out.println("业务层捕获错误了!");
			e.printStackTrace();
			return ImageVO.fail();
		}
		
	}

}
