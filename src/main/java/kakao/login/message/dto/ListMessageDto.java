package kakao.login.message.dto;

import lombok.Data;

import java.util.List;

@Data
public class ListMessageDto {

    private String headerTitle;
    private String title;
    private String description;
    private String imageUrl;
    private String imageWidth;
    private String imageHeight;
    private String webUrl;
    private String mobileUrl;
    private List<ListMessageDto> dtoList;
}
