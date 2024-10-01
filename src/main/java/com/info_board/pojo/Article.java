package com.info_board.pojo;


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
    @Pattern(regexp = "^\\S{1,10}$")
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
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public interface Add extends Default {

    }

    public interface Update extends Default{

    }
}
