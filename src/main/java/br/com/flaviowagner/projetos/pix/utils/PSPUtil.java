package br.com.flaviowagner.projetos.pix.utils;

import br.com.flaviowagner.projetos.pix.annotations.PSP;
import br.com.flaviowagner.projetos.pix.config.AppConfig;
import br.com.flaviowagner.projetos.pix.model.PSPInfo;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PSPUtil {
    public static List<PSPInfo> listarPSPs() {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false);

        scanner.addIncludeFilter(new AnnotationTypeFilter(PSP.class));

        Set<BeanDefinition> classes = scanner.findCandidateComponents("br.com.flaviowagner.projetos.pix.psp");

        var resultado = classes.stream().map(x -> {
            try {
                Class<?> clazz = Class.forName(x.getBeanClassName());
                PSP annotation = clazz.getAnnotation(PSP.class);
                return new PSPInfo(annotation.codigoBanco(), annotation.nomeBanco(), x.getBeanClassName());
            } catch (ClassNotFoundException e) {
                return null;
            }
        }).collect(Collectors.toList());

        return resultado;
    }

    public static boolean isProducao(){
        try {
            return Boolean.parseBoolean(AppConfig.getProperty("pix.ambiente.producao"));
        }catch(Exception e){
            return false;
        }
    }
}
