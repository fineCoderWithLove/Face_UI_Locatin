package com.example.face_ui_location.server;

import com.example.face_ui_location.vo.FaceResult;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import com.tencentcloudapi.iai.v20180301.IaiClient;
import com.tencentcloudapi.iai.v20180301.models.CompareFaceRequest;
import com.tencentcloudapi.iai.v20180301.models.CompareFaceResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author tanyongpeng
 * <p>调用腾讯接口</p>
 **/
@Component
@Data
@Slf4j
public class FaceContrastServer {

    @Value("AKID6ED6D7lEVXLvJWVI1Xqvn5A9wYw8A3oW")
    private String secretId;
    @Value("kIcibFLGza3MrBHApJcmlaDdpqiNnzzm")
    private String secretKey;
    @Value("iai.tencentcloudapi.com")
    private String endpoint;
    @Value("ap-shanghai")
    private String region;

    public FaceResult faceContrast(String imageA, String imageB){
        FaceResult faceResult = new FaceResult();
        try{
            Credential cred = new Credential(secretId, secretKey);
            HttpProfile httpProfile = new HttpProfile();
            httpProfile.setEndpoint(endpoint);
            ClientProfile clientProfile = new ClientProfile();
            clientProfile.setHttpProfile(httpProfile);
            IaiClient client = new IaiClient(cred, region, clientProfile);
            CompareFaceRequest req = new CompareFaceRequest();
            req.setImageA(imageA);
            req.setImageB(imageB);
            CompareFaceResponse resp = client.CompareFace(req);
            faceResult.setScore(resp.getScore());
            faceResult.setCode(FaceResult.SUCCESS_CODE);
        } catch (TencentCloudSDKException e) {
            faceResult.setCode(FaceResult.FACE_ERROR);
            faceResult.setMsg(e.getMessage());
        }
        return faceResult;
    }
}
