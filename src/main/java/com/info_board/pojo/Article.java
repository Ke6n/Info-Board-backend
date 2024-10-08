package com.info_board.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.info_board.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

@Data
public class Article {
    @NotNull(groups = {Update.class})
    private Integer id;//ID(PK)

    @NotEmpty
    @Pattern(regexp = "^.{1,50}$")
    private String title;

    @NotEmpty
    private String content;
    @NotEmpty
    @URL
    private String coverImg;

    @State
    private String state;//Draft|Published

    @NotNull
    private Integer categoryId;
    private Integer createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}
