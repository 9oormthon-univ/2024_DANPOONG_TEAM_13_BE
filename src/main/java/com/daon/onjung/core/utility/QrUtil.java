package com.daon.onjung.core.utility;

import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class QrUtil {

    // QR을 확인해야 하는 웹 서버 주소
    @Value("${qr-web.url}")
    private String url;
    @Value("${qr-web.path}")
    private String path;

    @Value("${qr-web.logo_img_url}")
    private String logo_img_url;
    @Value("${qr-web.bg_img_url}")
    private String bg_img_url;

    @Value("${qr-web.qr-color-code}")
    private String qr_color_code;
    @Value("${qr-web.bg-color-code}")
    private String bg_color_code;

    /**
     * 주어진 링크를 인코딩하여 QR 코드 이미지를 생성하고,
     * 배경 이미지와 로고를 결합하여 byte 배열 형태로 반환하는 메서드
     *
     * @param id
     * @return 배경 이미지와 합성된 QR 코드 이미지를 바이트 배열 형태로 반환
     */
    public byte[] generateQrCodeImageByte(String id) {
        try {

            // QR 코드 데이터 생성
            String baseUrl = url + path + id;

            // QR 코드 생성 옵션 설정
            Map<EncodeHintType, Object> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.MARGIN, 0); // 여백 없음
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            // QR 코드 생성
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            int qrSize = 153; // QR 코드와 배경 이미지 크기를 동일하게 설정
            BitMatrix bitMatrix = qrCodeWriter.encode(baseUrl, BarcodeFormat.QR_CODE, qrSize, qrSize, hintMap);

            // 색상 지정
            MatrixToImageConfig matrixToImageConfig = new MatrixToImageConfig(Long.decode(qr_color_code).intValue(),Long.decode(bg_color_code).intValue());

            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix, matrixToImageConfig);

            // 배경 이미지 로드 (디자인 파일 경로) - 배경이 없으면 로고 이미지가 흰색으로 안 보이는 이슈 있음.
            BufferedImage originalBackgroundImage = ImageIO.read(new File(bg_img_url));

            // 배경 이미지를 QR 코드 크기로 리사이즈
            BufferedImage backgroundImage = new BufferedImage(qrSize, qrSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D bgGraphics = backgroundImage.createGraphics();
            bgGraphics.drawImage(originalBackgroundImage, 0, 0, qrSize, qrSize, null);
            bgGraphics.dispose();

            // 로고 이미지 로드
            BufferedImage logoImage = ImageIO.read(new File(logo_img_url));

            // 배경 이미지 위에 QR 코드 합성
            BufferedImage combinedImage = new BufferedImage(qrSize, qrSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = combinedImage.createGraphics();

            // 배경 이미지 그리기
            g.drawImage(backgroundImage, 0, 0, null);

            // QR 코드 중앙에 그리기
            int qrX = (qrSize - qrCodeImage.getWidth()) / 2;
            int qrY = (qrSize - qrCodeImage.getHeight()) / 2;
            g.drawImage(qrCodeImage, qrX, qrY, null);

            // QR 코드 중앙에 로고 삽입
            int logoWidth = qrCodeImage.getWidth() / 4; // QR 코드 크기의 1/4 크기로 로고 설정
            int logoHeight = qrCodeImage.getHeight() / 4;
            int logoX = qrX + (qrCodeImage.getWidth() - logoWidth) / 2; // QR 코드 중앙에 배치
            int logoY = qrY + (qrCodeImage.getHeight() - logoHeight) / 2;
            g.drawImage(logoImage, logoX, logoY, logoWidth, logoHeight, null);

            // 리소스 해제
            g.dispose();

            // 결과 이미지를 바이트 배열로 변환
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(combinedImage, "png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] combinedImageBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();

            return combinedImageBytes;

        } catch (WriterException e) {
            throw new CommonException(ErrorCode.QR_CODE_GENERATION_ERROR);
        } catch (IOException e) {
            throw new CommonException(ErrorCode.QR_CODE_IMAGE_PROCESSING_ERROR);
        }
    }
}
