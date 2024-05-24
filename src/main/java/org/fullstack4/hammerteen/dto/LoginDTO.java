package org.fullstack4.hammerteen.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {

    @NotEmpty
    private String userId;
    @NotEmpty
    private String pwd;

    private String save_id;
    private String auto_login;
}
