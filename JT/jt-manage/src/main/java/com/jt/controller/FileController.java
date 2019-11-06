package com.jt.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.jt.service.FileService;
import com.jt.vo.ImageVO;

@Controller
public class FileController {

	/**
	 * 文件上传完成后重定向到上传页面
	 * 	步骤:
	 * 1.form表单 enctype="multipart/form-data"
	 * 2.接收参数要用MultipartFile对象
	 * 3.利用fileImage.transferTo(file)上传
	 * 步骤2:
	 * 	准备文件存储目录
	 * 	准备文件名称
	 * 	实现上传
	 * 	
	 * 	文件上传成功后,返回Json数据
	 * 	需要一个file io ...云云 反正比较麻烦,
	 * 	还好Spring给我们提供了一个api  Multi 对象名要写固定的fileImage
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	@RequestMapping("/file")
	public String file(MultipartFile fileImage) throws IllegalStateException, IOException {
	// 动态获取图片名称
	String fileName = fileImage.getOriginalFilename();
		/**
		* 定义文件夹
		* 创建文件对象,指定上传的目录(我的电脑中操作)
		* 保险机制,确保有文件夹
		 */
		File dir = new File("E:/1-jt-software/jt-images");
		if (!dir.exists()) {			// 如果文件不存在需要创建目录
			dir.mkdirs();			// mkdirs比mkdir还可以创建各种子孙目录,用这个用好
		}
		File file = new File("E:/1-jt-software/jt-images/" + fileName);
		// 上传api
		fileImage.transferTo(file);
		return "redirect:/file.jsp";
	}
	
	@Autowired
	private FileService fileService;
	@RequestMapping("/pic/upload")
	@ResponseBody
	public ImageVO fileUpload(MultipartFile uploadFile) {
		ImageVO upload = fileService.upload(uploadFile);
		System.out.println("图片上传的控制层返回结果:" + upload);
		return upload;
	}
}
