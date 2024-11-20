package com.daon.onjung.core.utility;

import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class QrUtil {

    // qr을 확인해야하는 웹 서버 주소
    @Value("${qr-web.url}")
    private String url;

    @Value("${qr-web.path}")
    private String path;

    /**
     * 주어진 링크를 인코딩하여 QR 코드 이미지를 생성하고,
     * 그 이미지를 byte 배열 형태로 반환하는 메서드
     * @param ticketId
     * @return QR 코드 이미지를 바이트 배열 형태로 변환
     * @throws WriterException
     * @throws IOException
     */
    public byte[] generateQrCodeImageByte(Long ticketId) {

        try {
            // QR 코드에 포함될 데이터 생성
            String baseUrl = url + path + ticketId;

            // QR코드 생성 옵션 설정
            Map<EncodeHintType, Object> hintMap = new HashMap<>();
            hintMap.put(EncodeHintType.MARGIN, 0);
            hintMap.put(EncodeHintType.CHARACTER_SET,"UTF-8");

            // QR 코드 생성
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix bitMatrix = qrCodeWriter.encode(baseUrl, BarcodeFormat.QR_CODE, 153, 153, hintMap);

            // QR 코드 이미지 생성
            BufferedImage qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // QR 코드 이미지를 바이트 배열로 변환, byteArrayOutputStream에 저장
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(qrCodeImage,"png", byteArrayOutputStream);
            byteArrayOutputStream.flush();
            byte[] qrCodeBytes = byteArrayOutputStream.toByteArray();
            byteArrayOutputStream.close();

            return qrCodeBytes;
        } catch (WriterException e) {
            throw new RuntimeException("Error occurred during QR code generation.", e);
        } catch (IOException e) {
            throw new RuntimeException("Error occurred while processing the QR code image.", e);
        }
    }
}
