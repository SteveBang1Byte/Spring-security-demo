/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created - dev29
 */

package com.steve.securitysecuritydemo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



/**
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * Description: ....
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class JWTResponse {
    private String accessToken;
    private String refreshToken;
}
