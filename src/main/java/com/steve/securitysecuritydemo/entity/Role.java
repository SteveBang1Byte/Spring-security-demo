/**
 * Description: ... <br/>
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * History: <br/>
 * - Created - dev29
 */

package com.steve.securitysecuritydemo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


/**
 * Created by: Steve Bang <br/>
 * Created date: 2023-05-10 <br/>
 * Description: ....
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
